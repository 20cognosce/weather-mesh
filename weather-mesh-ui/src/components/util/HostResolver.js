const prod = {
    WEATHER_ORIGIN: 'http://' + process.env.REACT_APP_GATEWAY_IP,
    CIRCUIT_BREAKER_ORIGIN: 'http://' + process.env.REACT_APP_GATEWAY_IP,
    AUTH_ORIGIN: 'http://' + process.env.REACT_APP_GATEWAY_IP
}

const dev = {
    WEATHER_ORIGIN: 'http://localhost:8080',
    CIRCUIT_BREAKER_ORIGIN: 'http://localhost:8082',
    AUTH_ORIGIN: 'http://localhost:8083'
}

export const config = process.env.REACT_APP_PROFILE === 'prod' ? prod : dev