import mongoose, { Schema, mongo } from 'mongoose';

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    email: {type: String, required: true},
    password: {type: String, required: true},
    displayName: {type: String, required: true},
    image: String,
    skills: [String],
    avaiable: Boolean,
    contactInfoList: [{kind: String, info: String}]
});


export default mongoose.model('User', userSchema);
