using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GovHacDal.Repository
{
    public static class ImageRepository
    {

        public static List<vwAccountLevels> GetAccountLevels(int accountId)
        {
            using (GovHacSpatialEntities entities = new GovHacSpatialEntities())
            {
                return entities.vwAccountLevels.Where(a => a.accountId == accountId).ToList();
            }
        }
    }
}
