import mongoose, { get } from "mongoose";
import Category from "../models/Category";
import config from "../../config/config";

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
//Return the IDS of the users inside this category
exports.getUserIdsInCategory = (req, res) => {
  Category.findOne({ type: req.params.type })
    .exec()
    .then((category) => {
      if (category) {
        //? for testing eval(locus);
        res.status(200).json(category.users);
      } else
        res.status(404).json({
          message: "No such Category with this type.",
        });
    })
    .catch((err) => {
      res.status(500).json({
        error: err,
      });
    });
};

//Remove an ID from the users list inside the category
//exports.removeUserIdFromCategory = (req, res) => {
//  Category.findOneAndUpdate(
//    { type: req.params.type },
//    { users: req.body.users }
//  )
//    .exec()
//    .then((category) => {
//      res.status(200).json({
//        message: `Updated users list in ${category.type} category`
//      });
//    });
//};
exports.removeUserIdFromCategory = (req, res) => {
  Category.findOneAndUpdate(
    { type: req.params.type },
    { $pull: { users: req.params.userId } }
  )
    .exec()
    .then((category) => {
      res.status(200).json({
        message: `Deleted id from the list of users in ${category.type} category`
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
    { $push: { users: req.params.userId } }
  )
    .exec()
    .then((category) => {
      res.status(200).json({
        message: `user was added to the users array on ${category.type} object`,
      });
    });
};

//takes a type on the parameter
exports.deleteCategory = (req, res) => {
  Category.remove({ type: req.params.type })
    .exec()
    .then((category) => {
      res.status(200).json({
        message: "Category Deleted",
        request: {
          type: "GET",
          url: config.hostUrl + "/categories"
        },
      });
    })
    .catch((err) => {
      res.status(500).json({
        error: err
      });
    });
};
