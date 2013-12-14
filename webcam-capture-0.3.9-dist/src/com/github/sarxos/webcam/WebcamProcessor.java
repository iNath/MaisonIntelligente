package com.github.sarxos.webcam;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;


public class WebcamProcessor {

	/**
	 * Thread factory for processor.
	 * 
	 * @author Bartosz Firyn (SarXos)
	 */
	private static final class ProcessorThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r, "atomic-processor");
			t.setDaemon(true);
			return t;
		}
	}

	/**
	 * Heart of overall processing system. This class process all native calls
	 * wrapped in tasks, by doing this all tasks executions are
	 * super-synchronized.
	 * 
	 * @author Bartosz Firyn (SarXos)
	 */
	private static final class AtomicProcessor implements Runnable {

		private SynchronousQueue<WebcamTask> inbound = new SynchronousQueue<WebcamTask>(true);
		private SynchronousQueue<WebcamTask> outbound = new SynchronousQueue<WebcamTask>(true);

		/**
		 * Process task.
		 * 
		 * @param task the task to be processed
		 * @return Processed task
		 * @throws InterruptedException when thread has been interrupted
		 */
		public WebcamTask process(WebcamTask task) throws InterruptedException {
			inbound.put(task);
			return outbound.take();
		}

		@Override
		public void run() {
			while (true) {
				WebcamTask t = null;
				try {
					(t = inbound.take()).handle();
				} catch (InterruptedException e) {
					return;
				} finally {
					try {
						if (t != null) {
							outbound.put(t);
						}
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		}
	}

	/**
	 * Is processor started?
	 */
	private static final AtomicBoolean started = new AtomicBoolean(false);

	/**
	 * Execution service.
	 */
	private static final ExecutorService runner = Executors.newSingleThreadExecutor(new ProcessorThreadFactory());

	/**
	 * Static processor.
	 */
	private static final AtomicProcessor processor = new AtomicProcessor();

	/**
	 * Singleton instance.
	 */
	private static WebcamProcessor instance = null;

	private WebcamProcessor() {
	}

	/**
	 * Process single webcam task.
	 * 
	 * @param task the task to be processed
	 */
	public void process(WebcamTask task) {
		if (started.compareAndSet(false, true)) {
			runner.execute(processor);
		}
		try {
			processor.process(task);
		} catch (InterruptedException e) {
			throw new WebcamException("Processing interrupted", e);
		}
	}

	public static synchronized WebcamProcessor getInstance() {
		if (instance == null) {
			instance = new WebcamProcessor();
		}
		return instance;
	}
}
