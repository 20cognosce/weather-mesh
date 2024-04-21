const prod = {
    WEATHER_BASE_URL: 'http://localhost:8080/weather',
    CIRCUIT_BREAKER_BASE_URL: 'http://localhost:8082/circuit-breaker',
    AUTH_BASE_BASE_URL: process.env.AUTH_BASE_URL
}

const dev = {
    WEATHER_BASE_URL: 'http://localhost:8080/weather',
    CIRCUIT_BREAKER_BASE_URL: 'http://localhost:8082/circuit-breaker',
    AUTH_BASE_URL: 'http://localhost:8083/auth'
}

export const config = process.env.NODE_ENV === 'development' ? dev : dev