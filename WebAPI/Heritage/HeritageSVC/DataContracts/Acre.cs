using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;
using System.Spatial;

namespace HeritageSVC.DataContracts
{
    [DataContract]
    public class Acre
    {
        [DataMember]
        public long ArceId { get; set; }
        [DataMember]
        public Geography Geolocation { get; set; }
        [DataMember]
        public string Description { get; set; }
        [DataMember]
        public string Number { get; set; }
    }
}