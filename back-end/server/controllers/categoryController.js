import mongoose from "mongoose";
import Category from "../models/Category";
import config from "../../config/config";
import locus from "locus";

exports.getCategories = (req, res) => {
  Category.find()
    .select()
    .exec()
    .then((categories) => res.status(200).json(categories))
    .catch((err) => {
      res.status(500).json({
        error: err,
      });
    });
};

exports.getUsersInCategory = (req, res) => {
  Category.findOne({ type: req.params.type })
    .populate("users")
    .exec()
    .then((category) => {
      if (category) {
        //? for testing eval(locus);
        res.status(200).json(category.users);
      } else
        res.status(404).json({
          message: "No such Category with this ID.",
        });
    })
    .catch((err) => {
      res.status(500).json({
        error: err,
      });
    });
};

exports.addCategory = (req, res) => {
  const category = new Category({
    _id: new mongoose.Types.ObjectId(),
    type: req.body.type,
    subCategories: req.body.subCategories,
    users: [],
  });

  category
    .save()
    .then((category) => {
      res.status(201).json({
        message: "Created category successfully",
        createdCategory: {
          _id: category._id,
          type: category.type,
          subCategories: category.subCategories,
          users: [],
          request: {
            type: "GET",
            url: config.hostUrl + "/categories/" + category._id,
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

//take a body of _id:
exports.updateCategory = (req, res) => {
  Category.findOneAndUpdate(
    { type: req.params.type },
    { $push: { users: req.body._id } }
  )
    .exec()
    .then((category) => {
      res.status(200).json({
        message: `user was added to the users array on ${category.type} object`,
      });
    });
};
