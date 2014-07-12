using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Quartz;
using Quartz.Impl;

namespace Heritage.Schedule
{
    public static class ScheduleUtil
    {
        private const string LOCK_OBJECT = "Lock";

        private readonly static IScheduler sched;

        static ScheduleUtil()
        {
            lock (LOCK_OBJECT)
            {
                if (sched != null) return;

                ISchedulerFactory sf = new StdSchedulerFactory();
                sched = sf.GetScheduler();
            }
        }

        public static void ScheduleJob<T>(string cronExpression) where T : IJob
        {
            string className = typeof(T).Name;

            // create the job detail
            IJobDetail jobDetail = JobBuilder.Create<T>()
                .WithIdentity("job" + className, "group" + className)
                .Build();

            // set up the trigger.
            ICronTrigger trigger = (ICronTrigger)TriggerBuilder.Create()
                .WithIdentity("trigger" + className, "group" + className)
                .WithCronSchedule(cronExpression)
                .Build();


            if (!sched.CheckExists(trigger.Key))
            {
                // add the job to the scheduler if it does not already exist.
                DateTimeOffset ft = sched.ScheduleJob(jobDetail, trigger);
            }
        }

        public static void StartJobs()
        {
            if (sched != null && !sched.IsStarted)
            {
                sched.Start();
            }
        }
    }
}