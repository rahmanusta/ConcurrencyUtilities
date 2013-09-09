package com.kodcu;

import org.quartz.CronExpression;

import javax.annotation.Resource;
import javax.enterprise.concurrent.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: usta
 * Date: 26.07.2013
 * Time: 01:56
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(urlPatterns = "/kodcu",name = "KodcuServlet")
public class KodcuServlet extends HttpServlet {

    @Resource
    private ManagedExecutorService defaultmanagedExecutorService;

    @Resource
    private ManagedScheduledExecutorService defaultScheduledExecutorService;

    @Resource(lookup = "concurrent/KodcuExecutor")
    private ManagedExecutorService managedExecutorService;

    @Resource(lookup = "concurrent/KodcuScheduledExecutor")
    private ManagedScheduledExecutorService scheduledExecutorService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            scheduledExecutorService
                    .schedule(new Gorev(), new CronTrigger(new CronExpression("0/5 * * * * ?")));

            List<Callable<Void>> gorevler=new ArrayList<>();

            for(int i=0;i<100;i++)
                gorevler.add(new Gorev2(i));

            defaultmanagedExecutorService.invokeAll(gorevler);

            Thread.currentThread().join();

        } catch (InterruptedException | ParseException e) {
            e.printStackTrace();
        }

    }
}
