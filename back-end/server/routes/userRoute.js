import userController from "../controllers/userController";
import express from "express";
import multer from "multer";
const storage = multer.diskStorage({
  //Adjust how files get stored
  destination: (req, file, cb) => {
    cb(null, "./uploads/");
  },
  filename: (req, file, cb) => {
    cb(null, new Date().toISOString().replace(/:/g, '-') + file.originalname);
  },
});

const fileFilter = (req, file, cb) => {
  //reject a file
  if (file.mimetype === "image/jpeg" || file.mimetype === "image/png") {
    cb(null, true);
  } else {
    cb(new Error('Invalid image type'), false);
  }
};

const upload = multer({
  storage: storage,
  limits: {
    fileSize: (1024 * 1024) / 2, //Only half a megabyte maximum
  },
  fileFilter: fileFilter
});

let router = express.Router();

//Get a list of all users
router.get("/", userController.getUsers);
//Get a particular user with their unique ID
router.get("/:id", userController.getUser);
//Add a user and get that added user back as a response
router.post("/", upload.single("userImage"), userController.addUser);
//Add the skills to the skills array in the User object
router.patch("/:email", userController.addUserSkills);
//Remove a user by passing their id as a paramter
router.delete("/:id", userController.deleteUser);

export default router;
