package boomerang.communication.threadpool;

import java.util.List;

public interface ITaskExecutor<R> {
    List<R> executeTasks();
}
