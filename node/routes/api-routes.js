const { v4: uuid } = require("uuid");
const express = require("express");
const router = express.Router();

const usersDao = require("../modules/users-dao.js");

let bodyParser = require('body-parser');
const { route } = require("./application-routes.js");

router.use(bodyParser.json());



// Post login page
router.post("/api/login", async function (req, res) {
    const username = req.body.username;
    const password = req.body.password;

    const user = await usersDao.retrieveUserWithCredentials(username, password);

    if (user) {
        // Auth success - give that user an authToken, save the token in a cookie
        const authToken = uuid();
        user.authToken = authToken;
        await usersDao.updateUser(user);
        res.cookie("authToken", authToken);

        res.status(204).json();
    }
    // No matching user...
    else {
        // Auth fail
        res.status(401).send();
    }
});



// GET Logout
router.get("/api/logout", function (req, res){
    res.clearCookie("authToken");
    res.status(204).send();
});



// GET list of users
router.get("/api/users", async function(req, res){
    try {
        const userList = await usersDao.retrieveAllUsers();
        res.json(userList);
    } catch (err) {
        res.status(401).json();
    }
});



// DELETE user
router.delete("/api/users/:id", async function(req, res){
    // If valid user / admin
    const user = res.locals.user;

    if (user) {
        try {
            await usersDao.deleteUser(res.locals.user.userId);
            res.clearCookie("authToken");
            res.locals.user = null;
            res.status(204).json();
        }
        catch (err) {
            res.status(401).json();
        }
    
    // User does not exist
    }else {
        // Auth fail
        res.locals.user = null;
        res.status(401).json();
    }
});


module.exports = router;