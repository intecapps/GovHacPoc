using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;
using System.Spatial;

namespace HeritageSVC.DataContracts
{
    [DataContract]
    public class Image
    {
        [DataMember]
        public long ImageId { get; set; }
        [DataMember]
        public string Name { get; set; }
        [DataMember]
        public string Description { get; set; }
        [DataMember]
        public long AcreID { get; set; }
    }
}