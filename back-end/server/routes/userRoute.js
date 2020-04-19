import userController from '../controllers/userController';
import express from 'express';

let router = express.Router();

//Get a list of all users
router.get("/", userController.getUser);
//Add a user and get that added user back as a response
router.post("/", userController.addUser);







export default router;