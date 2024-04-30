import axios from 'axios'
import {config} from '../util/HostResolver'

export const circuitBreakerApi = {
    getHealth,
    getAudit,
    getRequests,
    getSystems,
    getPermissions,
    getPermissionById,
    patchPermissionById
}

function getHealth() {
    return axiosInstance.get(
        '/actuator/health'
    )
}

function getAudit(token) {
    return axiosInstance.get(
        '/audit',
        {headers: {"Authorization": `${token}`}}
    )
}

function getRequests(token) {
    return axiosInstance.get(
        '/requests',
        {headers: {"Authorization": `${token}`}}
    )
}

function getSystems(token) {
    return axiosInstance.get(
        '/systems',
        {headers: {"Authorization": `${token}`}}
    )
}

function getPermissions(token) {
    return axiosInstance.get(
        '/permissions',
        {headers: {"Authorization": `${token}`}}
    )
}

function getPermissionById(token, id) {
    return axiosInstance.get(
        `/permissions?id=${id}`,
        {headers: {"Authorization": `${token}`}}
    )
}

function patchPermissionById(token, patchPermissionDto) {
    return axiosInstance.patch(
        '/permissions',
        patchPermissionDto,
        {headers: {"Authorization": `${token}`}}
    )
}

const axiosInstance = axios.create({
    baseURL: config.CIRCUIT_BREAKER_ORIGIN + '/circuit-breaker'
})