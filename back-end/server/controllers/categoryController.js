import mongoose from "mongoose";
import Category from "../models/Category";
import config from '../../config/config';

exports.getCategories = (req, res) => {
  Category.find()
    .select()
    .exec()
    .then((categories) => {
      const response = {
        count: categories.length,
        result: categories,
      };
      res.status(200).json(response);
    })
    .catch((err) => {
      res.status(500).json({
        error: err,
      });
    });
};

exports.getCategory = (req, res) => {
    Category.findOne({ _id: req.params.id })
    .exec()
    .then((category) => {
      if (category) {
        res.status(200).json({
          _id: category._id,
          type: category.type,
          subCategories: category.subCategories
        });
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
    subCategories: req.body.subCategories
  });
  
    category.save()
    .then((category) => {
      res.status(201).json({
        message: "Created category successfully",
        createdCategory: {
          _id: category._id,
          type: category.type,
          subCategories: category.subCategories,
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
