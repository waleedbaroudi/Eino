import categoryController from "../controllers/categoryController";
import express from "express";

let router = express.Router();

//Get a list of all users
router.get("/", categoryController.getCategories);
//Get a the users in a category by the type of the category
router.get("/:type", categoryController.getUsersInCategory);
//Add a category and get that added category back as a response
router.post("/", categoryController.addCategory);
//Add a user ID to a categories
router.patch("/:type", categoryController.updateCategory);
export default router;
