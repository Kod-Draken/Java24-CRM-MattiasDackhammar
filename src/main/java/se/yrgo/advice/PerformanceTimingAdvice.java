package se.yrgo.advice;

import org.aspectj.lang.ProceedingJoinPoint;

public class PerformanceTimingAdvice {
    public Object performTimingMeasurement(ProceedingJoinPoint method)
            throws Throwable {
        //before
        long startTime = System.nanoTime();

        try {
            //proceed to target
            return method.proceed();
        } finally {
            //after
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            System.out.println("The method " +
                    method.getSignature().getName()
                    + " in the class "
                    + method.getTarget().getClass().getName()
                    + " took " +
                    timeTaken / 1000000);
        }
    }
}