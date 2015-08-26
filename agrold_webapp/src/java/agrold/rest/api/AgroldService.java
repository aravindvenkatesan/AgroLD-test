package agrold.rest.api;

import agrold.rest.api.sparqlaccess.APILib;
import agrold.rest.api.sparqlaccess.OntologyDAO;
import agrold.rest.api.sparqlaccess.QtlDAO;
import agrold.rest.api.sparqlaccess.GraphDAO;
import agrold.rest.api.sparqlaccess.ProteinDAO;
import agrold.rest.api.sparqlaccess.GeneDAO;
import agrold.rest.api.security.CORSResponseFilter;
import java.net.URISyntaxException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author tagny
 */
@Path("/1.0")
public class AgroldService extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public AgroldService() {
        register(CORSResponseFilter.class);
   //other registrations omitted for brevity
    }    
    /**
     * Show the API interactive documentation
     */
    @GET
    @Path("/")
    @Produces({MediaType.TEXT_HTML})
    public void help() {
        try {
            java.net.URI location = new java.net.URI("../api-doc.jsp");
            throw new WebApplicationException(Response.temporaryRedirect(location).build());
        } catch (URISyntaxException e) {
        }
    }
 
    // Ontologies
    @POST
    @Path("/ancestors/byId{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getAncestorById(@PathParam("_format") String format, @QueryParam("id") String id, @QueryParam("level") int level, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        return OntologyDAO.getAncestorById(id, level, page, pageSize, format);
    }
    
    @POST
    @Path("/parents/byId{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getParentById(@PathParam("_format") String format, @QueryParam("id") String id, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        // The parent is the ancestor at level 1
        return OntologyDAO.getAncestorById(id, 1, page, pageSize, format);
    }
    
    @POST
    @Path("/descendants/byId{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getDescendantsById(@PathParam("_format") String format, @QueryParam("id") String id, @QueryParam("level") int level, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        return OntologyDAO.getDescendantsById(id, level, page, pageSize, format);
    }
    
    @POST
    @Path("/children/byId{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getChildrenById(@PathParam("_format") String format, @QueryParam("id") String id, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        // The parent is the ancestor at level 1
        return OntologyDAO.getDescendantsById(id, 1, page, pageSize, format);
    }

    @POST
    @Path("/id/byOntoTerm{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getIdByOntoTerm(@PathParam("_format") String format, @QueryParam("ontoTerm") String ontoTerm, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        return OntologyDAO.getIdByOntoTerm(ontoTerm, page, pageSize, format);
    }
    
    @POST
    @Path("/ontoTerm/byId{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getOntoTermById(@PathParam("_format") String format, @QueryParam("id") String id, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        return OntologyDAO.getOntoTermById(id, page, pageSize, format);
    }

    // graphs
    @POST
    @Path("/graphs{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String listGraphs(@PathParam("_format") String format, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        return GraphDAO.listGraph( page, pageSize,format);
    }

    @POST
    @Path("/description{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getDescription(@PathParam("_format") String format, @QueryParam("uri") String resourceURI, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        return GraphDAO.getResourceDescription(resourceURI, page, pageSize, format);
    }

    // QTLs
    @POST
    @Path("/qtls{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getQtls(@PathParam("_format") String format, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        return QtlDAO.getAllQtlURI( page, pageSize, format);
    }
    @POST
    @Path("/qtls/id/associatedWithOntoId{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getQtlsIdAssociatedWithOntoId(@PathParam("_format") String format, @QueryParam("ontoId") String ontoId, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        return QtlDAO.getQtlIdAssociatedWithOntoId(ontoId, page, pageSize, format);
    }

    // Proteins
    @POST
    @Path("/proteins{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getProteins(@PathParam("_format") String format, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        return ProteinDAO.getAllProteinsURI( page, pageSize,format);
    }
    
    @POST
    @Path("/proteins/id/associatedWithOntoId{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getProteinsIdAssociatedWithOntoId(@PathParam("_format") String format, @QueryParam("ontoId") String ontoId, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        return ProteinDAO.getProteinsIdAssociatedWithOntoId(ontoId, page, pageSize, format);
    }

    // Genes
    @POST
    @Path("/genes{_format}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,APILib.TSV,APILib.CSV,MediaType.TEXT_XML,APILib.RDF_XML,APILib.TTL})
    public String getGenes(@PathParam("_format") String format, 
            @QueryParam("_page") int page, @QueryParam("_pageSize") int pageSize) {
        return GeneDAO.getAllGenesURI( page, pageSize, format);
    }


    /*// Predicates
    @POST
    @Path("/predicates.xml")
    @Produces({MediaType.APPLICATION_XML})
    public List<Predicate> getPredicates() {
        return predicateDAO.getAllPredicates();
    }

    @POST
    @Path("/predicates.json")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Predicate> getPredicatesJson() {
        return predicateDAO.getAllPredicates();
    }

    @POST
    @Path("/predicates.cache")
    @Produces(MediaType.APPLICATION_XML)
    public Response getPredicatesCache() {
        Response.ResponseBuilder response = Response.ok(predicateDAO.getAllPredicates());//.type(MediaType.APPLICATION_XML);
        // Expires 3 seconds from now..this would be ideally based
        // of some pre-determined non-functional requirement.        
        Date expirationDate = new Date(System.currentTimeMillis() + 3000);
        response.expires(expirationDate);

        return response.build();
    }*/
}
