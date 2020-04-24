import categoryController from "../controllers/categoryController";
import express from "express";

let router = express.Router();

//Get a list of all users
router.get("/", categoryController.getCategories);
//Get a the users in a category by the type of the category
router.get("/:type", categoryController.getUsersInCategory);
//Add a user and get that added user back as a response
router.post("/", categoryController.addCategory);

export default router;
