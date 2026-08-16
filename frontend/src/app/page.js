import Banner from "@/Component/Banner";
import FeaturedCategory from "@/Component/FeaturedCategory";

export default function Home() {
  return (<>
    <Banner />
    <div className="flex flex-row gap-1 p-10">
    <FeaturedCategory />
    </div>
    </>
  );
}
