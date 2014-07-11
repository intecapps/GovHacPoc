using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;
using System.Spatial;

namespace HeritageSVC
{
    [DataContract]
    public class PlaceMarkerType
    {
        [DataMember]
        public long PlaceMarkerTypeId { get; set; }
        [DataMember]
        public string Name { get; set; }
        [DataMember]
        public string Description { get; set; }
    }
}