import categoryController from "../controllers/categoryController";
import express from "express";

let router = express.Router();

//Get a list of all users
router.get("/", categoryController.getCategories);
//Get a list of user objects in a category by the type of the category
router.get("/:type", categoryController.getUsersInCategory);
//Get a list of user Ids in a category by the type of the category
router.get("/id/:type", categoryController.getUserIdsInCategory);
//Add a category and get that added category back as a response
router.post("/", categoryController.addCategory);
//Add a user ID to a category
router.patch("/:type", categoryController.updateCategory);
//Remove a user ID from a category.
router.patch("/id/:type/:userId", categoryController.removeUserIdFromCategory);
//Remove a category depending on type
router.delete("/:type", categoryController.deleteCategory);
export default router;
