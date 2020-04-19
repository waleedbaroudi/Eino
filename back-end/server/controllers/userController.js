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
