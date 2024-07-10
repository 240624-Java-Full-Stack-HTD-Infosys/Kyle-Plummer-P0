package com.revature.p0;

import com.revature.p0.utils.ServerUtil;

import java.io.IOException;
import java.sql.SQLException;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Driver.class);
    private static final int PORT = 7777;

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        LOGGER.info("Server starting on port: " + PORT);
        ServerUtil.getServerUtil().initialize(PORT);


        //ServerUtil.getServerUtil().executeSqlScript("create_users_table.sql");
    }

}
