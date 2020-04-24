import mongoose from "mongoose";
import User from "../models/User";
import config from "../../config/config";
import locus from "locus";

exports.getUsers = (req, res) => {
  User.find()
    .select()
    .exec()
    .then((users) => {
      res.status(200).json(users);
    })
    .catch((err) => {
      res.status(500).json({
        error: err,
      });
    });
};

exports.getUser = (req, res) => {
  User.findOne({ _id: req.params.id })
    .exec()
    .then((user) => {
      if (user) {
        res.status(200).json({
          _id: user._id,
          email: user.email,
          password: user.password,
          displayName: user.displayName,
          image: user.image,
          skills: user.skills,
          available: user.available,
          contactInfoList: user.contactInfoList,
        });
      } else
        res.status(404).json({
          message: "No such user with this ID.",
        });
    })
    .catch((err) => {
      res.status(500).json({
        error: err,
      });
    });
};

exports.addUser = (req, res) => {
  const user = new User({
    _id: new mongoose.Types.ObjectId(),
    email: req.body.email,
    password: req.body.password,
    displayName: req.body.displayName,
    image: req.body.image,
    skills: [],
    available: req.body.available,
    contactInfoList: req.body.contactInfoList,
  });
  user
    .save()
    .then((user) => {
      res.status(201).json({
        message: "Created user successfully",
        createdUser: {
          _id: user._id,
          email: user.email,
          password: user.password,
          displayName: user.displayName,
          image: user.image,
          skills: user.skills,
          available: user.available,
          contactInfoList: user.contactInfoList,
          request: {
            type: "GET",
            url: config.hostUrl + "users/" + user._id,
          },
        },
      });
    })
    .catch((err) => {
      res.status(500).json({
        error: err,
      });
    });
};

exports.addUserSkills = (req, res) => {
  User.findOneAndUpdate(
    { email: req.params.email },
    { $push: { skills: req.body.skills } }
  )
    .exec()
    .then((user) => {
      res.status(200).json({
        message: "Skills were added to the users object",
        skills: user.skills.concat(...req.body.skills) //?concat and the spread operator to print the current lost of skilss
      });
    });
};
