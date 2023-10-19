package boomerang.communication.threadpool;

public interface IExecutableTask<R> {
    R execute();
}
