using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Heritage.Models;

namespace Heritage.Helper
{
    public static class MockData
    {
        public static List<PlaceMarker> GetPlacemarker(string lattitude, string longitude)
        {
            List<PlaceMarker> markers = new List<PlaceMarker>();
 
            PlaceMarker marker = new PlaceMarker
            {
                Description="Awesome monument",
                Name = "Awesome monument",
                PlaceMarkerId=1,
                PlaceMarkerTypeID=1
            };

            PlaceMarker marker2 = new PlaceMarker
            {
                Description = "Awesome place",
                Name = "Awesome place",
                PlaceMarkerId = 1,
                PlaceMarkerTypeID = 1
            };

            markers.Add(marker);
            markers.Add(marker2);

            return markers;
        }

        public static List<Image> GetImages(string lattitude, string longitude, int placeMarkerId)
        {
            List<Image> images = new List<Image>();

            images.Add(new Image { Description = "Image 1", Name = "Image 1", ImageUrl = "http://images.slsa.sa.gov.au/mpcimg/06000/B5793.jpg" });
            images.Add(new Image { Description = "Image 2", Name = "Image 2", ImageUrl = "http://images.slsa.sa.gov.au/mpcimg/04750/B4525.jpg" });


            return images;
        }

        public static bool Checkin(int accountId, int placeMarkerId)
        {
            return true;
        }

        public static Level GetMyLevel(int accountId)
        {
            return new Level { ImageUrl = @"http://images.wikia.com/warframe/images/archive/f/f2/20130711224519!GrandMasterBadge.png", Name = "Grand Master G" };
        }
    }
}