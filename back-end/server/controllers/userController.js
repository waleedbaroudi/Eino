import mongoose from "mongoose";
import User from "../models/User";
import ContactInfo from "../models/ContactInfo";

exports.getUser = (req, res) => {
  User.find()
    .select()
    .exec()
    .then((users) => {
      const response = {
        count: users.length,
        result: users,
      };
      res.status(200).json(response);
    })
    .catch((err) => {
      res.status(500).json({
        error: err,
      });
    });
};
//Add a user and returns the added user
// _id: mongoose.Schema.Types.ObjectId,
// email: {type: String, required: true},
// password: {type: String, required: true},
// displayName: {type: String, required: true},
// image: String,
// skills: [String],
// avaiable: Boolean,
// contactInfoList: [{type: mongoose.Schema.Types.ObjectId, ref: 'ContactInfo'}]
exports.addUser = (req, res) => {
    const user = new User({
      _id: new mongoose.Types.ObjectId(),
      email: req.body.email,
      password: req.body.password,
      displayName: req.body.displayName,
      image: req.body.image,
      skills: req.body.skills,
      available: req.body.available,
    });
    user
      .save()
      .then(user => {
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
            request: {
              type: "GET",
              url: "http://localhost:3000/api/" + user._id
            }
          }
        });
      })
      .catch(err => {
        res.status(500).json({
          error: err
        });
      });
  };
