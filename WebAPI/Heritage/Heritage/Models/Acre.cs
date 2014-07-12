using System;
using System.Collections.Generic;
using System.Linq;
using System.Spatial;
using System.Web;

namespace Heritage.Models
{
    public class Acre
    {
        public long ArceId { get; set; }
        public Geography Geolocation { get; set; }
        public string Description { get; set; }
        public string Number { get; set; }
    }
}