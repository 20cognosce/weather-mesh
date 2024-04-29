const prod = {
    WEATHER_ORIGIN: process.env.GATEWAY_IP,
    CIRCUIT_BREAKER_ORIGIN: process.env.GATEWAY_IP,
    AUTH_ORIGIN: process.env.GATEWAY_IP
}

const dev = {
    WEATHER_ORIGIN: 'http://localhost:8080',
    CIRCUIT_BREAKER_ORIGIN: 'http://localhost:8082',
    AUTH_ORIGIN: 'http://localhost:8083'
}

export const config = process.env.NODE_PROFILE === 'prod' ? prod : dev