import React, {useContext} from 'react'
import {Icon, Menu} from 'semantic-ui-react'
import {Link} from "react-router-dom";
import AuthContext from "../auth/AuthContext";

function LayoutHeader() {
    const {isUserAuthenticated, getLogin, getRole} = useContext(AuthContext)

    return (
        <Menu stackable borderless size='massive' color={'black'} inverted>
            <Menu.Item position={"left"} header as={Link} to="/" style={{color: '#55ACEE'}}>
                Weather-Mesh-UI
            </Menu.Item>
            <Menu.Item fitted={"horizontally"} header> <Icon name='user outline'/>
                {isUserAuthenticated ? getLogin() : `unknown`}
            </Menu.Item>
            <Menu.Item header> <Icon name='tags'/>
                {isUserAuthenticated ? getRole() : `UNAUTHORIZED`}
            </Menu.Item>
        </Menu>
    )
}

export default LayoutHeader
