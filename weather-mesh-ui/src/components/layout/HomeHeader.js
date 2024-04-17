import React from 'react'
import {Icon, Menu} from 'semantic-ui-react'
import {Link} from "react-router-dom";

function HomeHeader() {
    return (
        <Menu stackable borderless size='massive' color={'black'} inverted>
            <Menu.Item position={"left"} header as={Link} to="/">
                Weather-Mesh-UI
            </Menu.Item>
            <Menu.Item position={"right"} header> <Icon name='user outline'/>
                {`Unauthorized`}
            </Menu.Item>
        </Menu>
    )
}

export default HomeHeader
