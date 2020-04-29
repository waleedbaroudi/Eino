import userController from "../controllers/userController";
import express from "express";
const {format} = require('util');
import {Storage} from '@google-cloud/storage';
import path from 'path';
import Multer from 'multer';

const gc = new Storage({
  keyFilename: path.join(__dirname, '../../eino-275710-13520d32986b.json'),
  projectId: 'eino-275710'
});

const einoImagesBucket = gc.bucket('einoimages');

// const multerStorage = multer.diskStorage({
//   //Adjust how files get stored
//   destination: (req, file, cb) => {
//     cb(null, "./uploads/");
//   },
//   filename: (req, file, cb) => {
//     cb(null, new Date().toISOString().replace(/:/g, '-') + file.originalname);
//   },
// });
const multer = Multer({
  storage: Multer.memoryStorage(),
  limits: {
    fileSize: 5 * 1024 * 1024, // no larger than 5mb, you can change as needed.
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

// const upload = multer({
//   storage: multerStorage,
//   limits: {
//     fileSize: (1024 * 1024) / 2, //Only half a megabyte maximum
//   },
//   fileFilter: fileFilter
// });

let router = express.Router();

//Get a list of all users
router.get("/", userController.getUsers);
//Get a particular user with their unique ID
router.get("/:id", userController.getUser);
//Add a user and get that added user back as a response
//router.post("/", upload.single("userImage"), userController.addUser);
router.post("/", multer.single("userImage"), (req, res, next) => {
  if (!req.file) {
    res.status(400).send('No file uploaded.');
    return;
  }
   // Create a new blob in the bucket and upload the file data.
   const blob = einoImagesBucket.file(req.file.originalname);
   const blobStream = blob.createWriteStream({
    metadata: {
      contentType: "image/jpeg" //Makes it so you view the image in the browser instead of downloading it.
    }
   });
 
   blobStream.on('error', (err) => {
     next(err);
   });
 
   blobStream.on('finish', () => {
     // The public URL can be used to directly access the file via HTTP.
     const publicUrl = format(
       `https://storage.googleapis.com/${einoImagesBucket.name}/${blob.name}`
     );
     res.status(200).send(publicUrl);
   });
 
   blobStream.end(req.file.buffer);
});
//Add the skills to the skills array in the User object
router.patch("/:email", userController.addUserSkills);
//Remove a user by passing their id as a paramter
router.delete("/:id", userController.deleteUser);

export default router;
