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
    
    public partial class Image
    {
        public Image()
        {
            this.Likes = new HashSet<Like>();
        }
    
        public int ID { get; set; }
        public string ImageUrl { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public Nullable<int> AcreID { get; set; }
        public string PublishDate { get; set; }
        public string Source { get; set; }
        public string RecordId { get; set; }
        public string RecordURL { get; set; }
        public string RecordXML { get; set; }
        public string Series { get; set; }
        public string Format { get; set; }
        public string Subject { get; set; }
        public string DateAccuracyNotes { get; set; }
        public string Provenance { get; set; }
    
        public virtual ICollection<Like> Likes { get; set; }
    }
}