package boomerang.communication.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ThreadPool<R> implements ITaskManager<R>, ITaskExecutor<R> {
    private int threads;
    private ExecutorService executor;
    private List<IExecutableTask<R>> tasks = new ArrayList<>();

    public ThreadPool(int threads) {
        this.threads = threads;
        this.executor = Executors.newFixedThreadPool(this.threads);
    }

    @Override
    public void submitTask(IExecutableTask<R> task) {
        this.tasks.add(task);
    }

    @Override
    public List<R> executeTasks() {
        try {
            List<Callable<R>> callables = this.tasks.stream()
                .map(this::toCallable)
                .collect(Collectors.toList());
            List<Future<R>> futures = this.executor.invokeAll(callables);
            List<R> responses = new ArrayList<>();
            for (Future<R> future : futures) {
                try {
                    responses.add(future.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return responses;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    Callable<R> toCallable(IExecutableTask<R> task) {
        return task::execute;
    }
}