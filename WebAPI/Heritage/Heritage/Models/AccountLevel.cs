using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Heritage.Models
{
    public class AccountLevel
    {
        public int AccountId { get; set; }

        public string Level { get; set; }

        public string Description { get; set; }

        public string Image { get; set; }
    }
}