import mongoose, { Schema, mongo } from 'mongoose';

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    email: {type: String, required: true},
    password: {type: String, required: true},
    displayName: {type: String, required: true},
    image: String,
    skills: [String],
    avaiable: Boolean,
    contactInfoList: [{type: mongoose.Schema.Types.ObjectId, ref: 'ContactInfo'}]
});

const contactInfoSchema = mongoose.Schema({
    user: {type: mongoose.Schema.Types.ObjectId, ref: 'User'},
    type: {type: String, required: true},
    info: {type: String, required: true}
});

export default mongoose.model('User', userSchema);
export default mongoose.model('ContactInfo', contactInfoSchema);