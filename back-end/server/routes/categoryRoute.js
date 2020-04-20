import categoryController from "../controllers/categoryController";
import express from "express";

let router = express.Router();

//Get a list of all users
router.get("/", categoryController.getCategories);
//Get a particular user with their unique ID
router.get("/:id", categoryController.getCategory);
//Add a user and get that added user back as a response
router.post("/", categoryController.addCategory);

export default router;
