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
    
    public partial class Like
    {
        public int ID { get; set; }
        public Nullable<int> ImageID { get; set; }
        public string AccountID { get; set; }
        public Nullable<int> PlaceMarkerID { get; set; }
    
        public virtual Image Image { get; set; }
        public virtual PlaceMarker PlaceMarker { get; set; }
    }
}
