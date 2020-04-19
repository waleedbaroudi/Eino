import userController from '../controllers/userController';
import express from 'express';

let router = express.Router();

//Get a list of all users
router.get("/", userController.getUser);








export default router;