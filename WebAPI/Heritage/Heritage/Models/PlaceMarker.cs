using System;
using System.Collections.Generic;
using System.Linq;
using System.Spatial;
using System.Web;

namespace Heritage.Models
{
    public class PlaceMarker
    {
        public long PlaceMarkerId { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public long PlaceMarkerTypeID { get; set; }
    }
    
}