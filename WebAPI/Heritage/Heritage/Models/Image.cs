using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Heritage.Models
{
    public class Image
    {
        public long ImageId { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public long AcreID { get; set; }
        public string ImageUrl { get; set; }
    }
}