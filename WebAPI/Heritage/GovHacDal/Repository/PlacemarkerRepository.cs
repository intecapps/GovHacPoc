using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GovHacDal.Repository
{
    public static class PlacemarkerRepository
    {
        public static IList<spGetPlaceMarker_Result> GetPlaceMarker(string lat, string lon)
        {
            using (GovHacSpatialEntities entities = new GovHacSpatialEntities())
            {
                return entities.spGetPlaceMarker(lat, lon).ToList();
            }
        }
    }
}
