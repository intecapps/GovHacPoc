using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Quartz;

namespace Heritage.Schedule
{
    [DisallowConcurrentExecution]
    public class LastKnownLocationJob : IJob
    {
        public void Execute(IJobExecutionContext context)
        {
            ProcessLastKnownLocations();
        }


        private void ProcessLastKnownLocations()
        {
            
        }
    }
}