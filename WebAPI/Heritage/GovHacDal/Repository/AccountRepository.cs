using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GovHacDal.Repository
{
    public static class AccountRepository
    {
        public static bool Checkin(int accountId, int placeMarkerId, string comment)
        {
            return true;
        }

        public static bool ImageView(int accountId, int imageId)
        {
            return true;
        }


        public static List<vwAccountLevels> GetAccountLevels(int accountId)
        {
            using (GovHacSpatialEntities entities = new GovHacSpatialEntities())
            {
                return entities.vwAccountLevels.Where(a => a.accountId == accountId).ToList();
            }
        }
    }
}
