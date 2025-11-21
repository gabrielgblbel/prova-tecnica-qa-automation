const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const { Pool } = require('pg');
const path = require('path');

const app = express();
const PORT = 8080;

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static('public'));

// Database configuration
const pool = new Pool({
    host: 'localhost',
    port: 5432,
    database: 'prova_tecnica_qa',
    user: 'postgres',
    password: 'postgres'
});

// In-memory storage for blocked users and login attempts
const loginAttempts = {};
const blockedUsers = new Set();

// Test users
const users = {
    'admin': { password: 'admin123', role: 'ADMIN' },
    'user': { password: 'user123', role: 'USER' },
    'visitor': { password: 'visitor123', role: 'VISITOR' }
};

// Helper function to check if user is blocked
function isUserBlocked(username) {
    return blockedUsers.has(username);
}

// Helper function to check database blocking
async function isUserBlockedInDB(username) {
    try {
        const result = await pool.query(
            'SELECT bloqueado FROM usuarios WHERE username = $1',
            [username]
        );
        return result.rows.length > 0 && result.rows[0].bloqueado === true;
    } catch (err) {
        console.log('DB check failed, using in-memory:', err.message);
        return isUserBlocked(username);
    }
}

// HTML Routes
app.get('/', (req, res) => {
    res.redirect('/login');
});

app.get('/login', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'login.html'));
});

app.get('/dashboard', (req, res) => {
    const role = req.query.role || 'USER';
    res.sendFile(path.join(__dirname, 'public', 'dashboard.html'));
});

// API Routes
app.post('/api/login', async (req, res) => {
    const { username, password } = req.body;

    console.log(`Login attempt: ${username}`);

    // Check if user is blocked in DB or memory
    const blockedInDB = await isUserBlockedInDB(username);
    if (blockedInDB || isUserBlocked(username)) {
        console.log(`User ${username} is blocked`);
        return res.status(423).json({ 
            message: 'User is blocked',
            error: 'Account locked due to multiple failed attempts'
        });
    }

    // Check credentials
    const user = users[username];
    if (!user || user.password !== password) {
        // Track failed attempts
        loginAttempts[username] = (loginAttempts[username] || 0) + 1;
        
        console.log(`Failed login for ${username}, attempts: ${loginAttempts[username]}`);
        
        // Block after 3 failed attempts
        if (loginAttempts[username] >= 3) {
            blockedUsers.add(username);
            console.log(`User ${username} blocked after 3 attempts`);
            
            // Update DB if exists
            try {
                await pool.query(
                    'UPDATE usuarios SET bloqueado = true, tentativas = $1 WHERE username = $2',
                    [loginAttempts[username], username]
                );
            } catch (err) {
                console.log('DB update failed:', err.message);
            }
            
            return res.status(423).json({ 
                message: 'User is blocked',
                error: 'Account locked due to multiple failed attempts'
            });
        }

        return res.status(401).json({ 
            message: 'Invalid credentials',
            attempts: loginAttempts[username]
        });
    }

    // Check role access
    if (user.role === 'VISITOR') {
        console.log(`Access denied for visitor: ${username}`);
        return res.status(403).json({ 
            message: 'Access denied',
            error: 'Insufficient permissions'
        });
    }

    // Reset attempts on successful login
    loginAttempts[username] = 0;

    // Log to database if exists
    try {
        await pool.query(
            'INSERT INTO auditoria_login (username, login_time, success) VALUES ($1, NOW(), true)',
            [username]
        );
    } catch (err) {
        console.log('DB logging failed:', err.message);
    }

    console.log(`Successful login for ${username}`);
    
    res.status(200).json({ 
        message: 'Login successful',
        token: `mock-token-${username}-${Date.now()}`,
        user: {
            username: username,
            role: user.role
        }
    });
});

// Reset user attempts (helper endpoint for tests)
app.post('/api/reset-attempts', async (req, res) => {
    const { username } = req.body;
    loginAttempts[username] = 0;
    blockedUsers.delete(username);
    
    try {
        await pool.query(
            'UPDATE usuarios SET bloqueado = false, tentativas = 0 WHERE username = $1',
            [username]
        );
    } catch (err) {
        console.log('DB reset failed:', err.message);
    }
    
    res.json({ message: `Reset attempts for ${username}` });
});

// Health check
app.get('/health', (req, res) => {
    res.json({ status: 'ok', timestamp: new Date() });
});

// Start server
app.listen(PORT, () => {
    console.log(`🚀 Mock Application running on http://localhost:${PORT}`);
    console.log(`📝 Login page: http://localhost:${PORT}/login`);
    console.log(`🔌 API endpoint: http://localhost:${PORT}/api/login`);
    console.log(`\n👤 Test Users:`);
    console.log(`   - admin / admin123 (ADMIN role)`);
    console.log(`   - user / user123 (USER role)`);
    console.log(`   - visitor / visitor123 (VISITOR - access denied)`);
});

// Database test
pool.query('SELECT NOW()', (err, res) => {
    if (err) {
        console.log('⚠️  Database connection failed (running without DB)');
    } else {
        console.log('✅ Database connected successfully');
    }
});
