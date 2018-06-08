package com.cmazxiaoma.seckill.controller;

import com.cmazxiaoma.seckill.result.Result;
import com.cmazxiaoma.seckill.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 16:10
 */
@RestController
@RequestMapping("/tasks")
public class TaskController extends BaseController {

    @Autowired
    private AsyncTask asyncTask;

    @RequestMapping("test")
    public Result test() throws Exception {
        long start = System.currentTimeMillis();

        Future<Boolean> a = asyncTask.doTask1();
        Future<Boolean> b = asyncTask.doTask2();
        Future<Boolean> c = asyncTask.doTask3();

        while (!a.isDone() || !b.isDone() || !c.isDone()) {
            if (a.isDone() && b.isDone() && c.isDone()) {
                break;
            }
        }
        long end = System.currentTimeMillis();
        String times = "任务全部完成，总耗时:" + (end - start) + "毫秒";
        return Result.success(times);
    }
}
