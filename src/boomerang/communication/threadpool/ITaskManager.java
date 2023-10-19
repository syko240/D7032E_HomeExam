package boomerang.communication.threadpool;

public interface ITaskManager<R> {
    void submitTask(IExecutableTask<R> task);
}
