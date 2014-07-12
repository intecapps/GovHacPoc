﻿//------------------------------------------------------------------------------
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
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    using System.Data.Entity.Core.Objects;
    using System.Data.Entity.Core.Objects.DataClasses;
    using System.Linq;
    
    public partial class GovHacSpatialEntities : DbContext
    {
        public GovHacSpatialEntities()
            : base("name=GovHacSpatialEntities")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public DbSet<AccountLevel> AccountLevels { get; set; }
        public DbSet<CheckIn> CheckIns { get; set; }
        public DbSet<Image> Images { get; set; }
        public DbSet<ImageView> ImageViews { get; set; }
        public DbSet<Level> Levels { get; set; }
        public DbSet<Like> Likes { get; set; }
        public DbSet<PlaceMarker> PlaceMarkers { get; set; }
        public DbSet<PlaceMarkerType> PlaceMarkerTypes { get; set; }
    
        public virtual ObjectResult<spGetPlaceMarker_Result> spGetPlaceMarker(string latitude, string longitude)
        {
            var latitudeParameter = latitude != null ?
                new ObjectParameter("Latitude", latitude) :
                new ObjectParameter("Latitude", typeof(string));
    
            var longitudeParameter = longitude != null ?
                new ObjectParameter("Longitude", longitude) :
                new ObjectParameter("Longitude", typeof(string));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction<spGetPlaceMarker_Result>("spGetPlaceMarker", latitudeParameter, longitudeParameter);
        }
    }
}
