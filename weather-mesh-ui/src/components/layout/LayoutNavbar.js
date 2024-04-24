import React, {useContext} from 'react'
import {Link} from 'react-router-dom'
import {Icon, Menu, MenuHeader, MenuItem, MenuMenu} from 'semantic-ui-react'
import AuthContext from '../auth/AuthContext'

function LayoutNavbar() {
    const {isUserAuthenticated, userLogout} = useContext(AuthContext)

    const handleLogout = () => {
        userLogout()
    }

    const loginMenuStyle = () => {
        return isUserAuthenticated ? {"display": "none"} : {"display": "block"}
    }

    const logoutMenuStyle = () => {
        return isUserAuthenticated ? {"display": "block"} : {"display": "none"}
    }

    return (
        <Menu vertical size='massive' color={"black"} fluid inverted>
            <MenuItem>
                <MenuHeader>Авторизация</MenuHeader>
                <MenuMenu>
                    <Menu.Item as={Link} to="/login" style={loginMenuStyle()}>
                        <Icon name='sign in'/>
                        Войти в систему
                    </Menu.Item>
                    <Menu.Item as={Link} to="/" style={logoutMenuStyle()} onClick={handleLogout}>
                        <Icon name='sign out'/>
                        Выйти из системы
                    </Menu.Item>
                </MenuMenu>
            </MenuItem>
            <MenuItem>
                <MenuHeader>Погода</MenuHeader>
                <MenuMenu>
                    <Menu.Item as={Link} to="/weather">
                        <Icon name='search'/>
                        Поиск
                    </Menu.Item>
                </MenuMenu>
            </MenuItem>
            <MenuItem>
                <MenuHeader>Управление трафиком</MenuHeader>
                <MenuMenu>
                    <Menu.Item as={Link} to="/permissions">
                        <Icon name='unlock'/>
                        Управление доступами
                    </Menu.Item>

                    <Menu.Item as={Link} to="/history">
                        <Icon name='file alternate'/>
                        История запросов
                    </Menu.Item>
                    <Menu.Item as={Link} to="/systems">
                        <Icon name='shutdown'/>
                        Системы
                    </Menu.Item>
                    <Menu.Item as={Link} to="/audit">
                        <Icon name='shield'/>
                        Аудит
                    </Menu.Item>
                </MenuMenu>
            </MenuItem>
            <MenuItem>
                <MenuHeader>Дополнительно</MenuHeader>
                <MenuMenu>
                    <Menu.Item as={Link} to="/about">
                        <Icon name='info circle'/>
                        О системе
                    </Menu.Item>
                    <Menu.Item as={Link} to="/author">
                        <Icon name='id card'/>
                        Об авторе
                    </Menu.Item>
                    <Menu.Item as={Link} to="/links">
                        <Icon name='globe'/>
                        Ссылки
                    </Menu.Item>
                </MenuMenu>
            </MenuItem>
        </Menu>
    )
}

export default LayoutNavbar
