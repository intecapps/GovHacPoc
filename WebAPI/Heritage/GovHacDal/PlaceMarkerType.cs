//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace GovHacDal
{
    using System;
    using System.Collections.Generic;
    
    public partial class PlaceMarkerType
    {
        public PlaceMarkerType()
        {
            this.PlaceMarker = new HashSet<PlaceMarker>();
        }
    
        public int ID { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
    
        public virtual ICollection<PlaceMarker> PlaceMarker { get; set; }
    }
}
