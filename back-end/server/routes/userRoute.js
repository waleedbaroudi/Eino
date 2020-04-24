import userController from '../controllers/userController';
import express from 'express';

let router = express.Router();

//Get a list of all users
router.get("/", userController.getUsers);
//Get a particular user with their unique ID
router.get("/:id", userController.getUser)
//Add a user and get that added user back as a response
router.post("/", userController.addUser);
//Add the skills to the skills array in the User object
router.patch("/:email", userController.addUserSkills);
//Remove a user by passing their id as a paramter
router.deleteUser("/:id", userController.deleteUser);






export default router;