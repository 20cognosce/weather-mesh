import axios from 'axios'
import {config} from './Constants'

export const advertApi = {
    login,
    signup,

    getUsersCount,
    getAdvertsCount,

    findAdvertByTitle,
    getImageUrlById,
    findUsersByEmail,

    deleteUser,
    activateUser,
    deleteAdvert,
    createAdvert,
    uploadImage
}

function signup(user) {
    return instance.post(
        '/auth/signup',
        user,
        {
            headers: {
                'Content-type': 'application/json'
            }
        })
}

function login(email, password) {
    return instance.post(
        '/auth/login',
        {email, password},
        {
            headers: {
                'Content-type': 'application/json'
            }
        })
}

function getUsersCount() {
    return instance.get('/users/count')
}

function getAdvertsCount() {
    return instance.get('/adverts/count')
}

function findAdvertByTitle(user, title) {
    const url = title ? `/adverts/find?title=${title}` : '/adverts';
    return instance.get(url, {
        headers: {'Authorization': basicAuth(user)}
    });
}

function getImageUrlById(id) {
    return instance.getUri() + `/images/${id}`
}

function findUsersByEmail(user, email) {
    const url = email ? `/users/find?email=${email}` : '/users'
    return instance.get(url, {
        headers: { 'Authorization': basicAuth(user) }
    })
}

function deleteUser(user, id) {
    return instance.post(`/users/delete/${id}`, null, {
        headers: { 'Authorization': basicAuth(user) }
    })
}

function activateUser(user, id) {
    return instance.post(`/users/activate/${id}`, null,{
        headers: { 'Authorization': basicAuth(user) }
    })
}

function deleteAdvert(user, id) {
    return instance.delete(`/adverts/${id}`, {
        headers: { 'Authorization': basicAuth(user) }
    })
}

function createAdvert(user, advert) {
    return instance.post('/adverts', advert, {
        headers: {
            'Content-type': 'application/json',
            'Authorization': basicAuth(user)
        }
    })
}

function uploadImage(user, imageFile, advertId) {
    let formData = new FormData();
    formData.append("image", imageFile)
    formData.append("advertId", advertId)
    formData.append("title", "")

    return instance.post('/images', formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': basicAuth(user)
        }
    })
}

const instance = axios.create({
    baseURL: config.url.API_BASE_URL
})

function basicAuth(user) {
    return `Basic ${user.authdata}`
}